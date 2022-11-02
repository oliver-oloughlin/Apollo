import AMQP from "https://esm.sh/amqplib@0.10.3"
import { SurrealDB } from "https://deno.land/x/deno_surreal@v1.5.1/mod.ts"

const db = new SurrealDB({
  host: "https://apollo.fly.dev",
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
      const poll = JSON.parse(msg.content.toString())
      const created = await db.create("poll", poll)
      console.log(created)
      ch1.ack(msg)
    }
    catch (err) {
      console.error(err)
    }
  }
})