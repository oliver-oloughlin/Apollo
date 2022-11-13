import AMQP from "https://esm.sh/amqplib@0.10.3"
import { SurrealDB } from "https://deno.land/x/deno_surreal@v1.6.0/mod.ts"

const db = new SurrealDB({
  host: "apollo.fly.dev",
  port: 8080,
  user: "root",
  pass: "anderserliten69",
  namespace: "apollo",
  database: "apollo"
})

const conn = await AMQP.connect({
  hostname: "hawk.rmq.cloudamqp.com",
  username: "glvuxjzx",
  vhost: "glvuxjzx",
  password: "53ptrDVEGoWII4-bsk7da-jg0R8VKFeo",
  port: 5672
})

type Answer = {
  question: string,
  yes: number,
  no: number
}

type Poll = {
  title: string,
  answers: Answer[]
}

const queue = "PollQueue"

const ch1 = await conn.createChannel()
await ch1.assertQueue(queue, {
  autoDelete: false,
  durable: false,
  exclusive: false
})

ch1.consume(queue, async (msg) => {
  if (msg) {
    try {
      const poll = JSON.parse(msg.content.toString()) as Poll
      const answers = poll.answers

      const totalYes = answers.reduce((acc, { yes }) => acc + yes, 0)
      const totalNo = answers.reduce((acc, { no }) => acc + no, 0)
      const totalVotes = totalYes + totalNo
      const avgYes = totalYes / answers.length
      const avgNo = totalNo / answers.length

      const created = await db.create("poll", {
        title: poll.title,
        totalQuestions: answers.length ?? 0,
        totalVotes: totalVotes ?? 0,
        totalYes: totalYes ?? 0,
        totalNo: totalNo ?? 0,
        avgYes: avgYes ?? 0,
        avgNo: avgNo ?? 0
      })
      
      console.log(created)
      ch1.ack(msg)
    }
    catch (err) {
      console.error(err)
    }
  }
})