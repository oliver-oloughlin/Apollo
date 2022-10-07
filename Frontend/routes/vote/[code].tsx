
import App from "../../components/App.tsx"
import { Style } from "fresh_utils"
import { Head } from "$fresh/runtime.ts"
import { Poll, Question } from "../../utils/models.ts"
import { Handlers, PageProps } from "$fresh/server.ts"

interface Data {
  poll: Poll,
  questions: Question[]
}

export const handler: Handlers<Data> = {
  GET: async (_req, ctx) => {
    const { code } = ctx.params
    const res = await fetch(`http://localhost:8080/poll/${code}`)
    const poll = await res.json() as Poll
    console.log(poll);
    if (!poll) return ctx.renderNotFound()
    const qFetchers = poll.questionIds.map(qid => fetch(`http://localhost:8080/question/${qid}`).then(res => res.json()))
    const questions = await Promise.all(qFetchers) as Question[]
    console.log(questions);
    return ctx.render({
      poll,
      questions
    })
  }
}

export default function VotePage({ data: { poll, questions } }: PageProps<Data>) {
  return (
    <App>
      <Head>
        <Style fileName="vote.css" />
      </Head>
      <main class="page box-bg">
        <div class="poll-container">
          <div class="question-container centered-text">
            <p>{questions[0]}</p>
          </div>
          <div class="switch">
            <button class="btn-red switch-btn" />
            <button class="btn-blue switch-btn" />
          </div>
        </div>
      </main>
    </App>
  )
}