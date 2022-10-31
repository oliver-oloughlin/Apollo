import App from "../components/App.tsx"
import { Style } from "fresh_utils"
import { Head } from "$fresh/runtime.ts"
import { Poll, Question } from "../utils/models.ts"
import { Handlers, PageProps } from "$fresh/server.ts"
import { API_HOST } from "../utils/api.ts"

interface Data {
  poll: Poll,
  questions: Question[]
}

export const handler: Handlers<Data> = {
  GET: async (req, ctx) => {
    const code = new URL(req.url).searchParams.get("code")
    const res = await fetch(`${API_HOST}/poll/${code}`)
    const poll = await res.json() as Poll
    if (!poll) return ctx.renderNotFound()
    const qFetchers = poll.questionIds.map(qid => fetch(`${API_HOST}/question/${qid}`).then(res => res.json()))
    const questions = await Promise.all(qFetchers) as Question[]
    return ctx.render({
      poll,
      questions
    })
  }
}

export default function VotePage({ data: { poll, questions } }: PageProps<Data>) {
  const [ question ] = questions
  return (
    <App>
      <Head>
        <Style fileName="vote.css" />
      </Head>
      <main class="page box-bg">
        <div class="poll-container">
          <div class="question-container centered-text">
            <p>{question?.text}</p>
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