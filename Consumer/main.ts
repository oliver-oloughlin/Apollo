import AMQP from "amqplib"
import { SurrealDB } from "deno_surreal"

const db = new SurrealDB({
  host: "https://apollo.fly.dev",
  user: "root",
  pass: "anderserliten69",
  namespace: "test",
  database: "test"
})

async function run() {
  const conn = await AMQP.connect("amqp://localhost")
  const queue = "PollQueue"

  // Consumer
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
}

run()