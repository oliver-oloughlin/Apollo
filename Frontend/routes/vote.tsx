import App from "../components/App.tsx"
import VoteView from "../islands/VoteView.tsx"
import { Style } from "fresh_utils"
import { Head } from "$fresh/runtime.ts"
import { Poll, Question } from "../utils/models.ts"
import { Handlers, PageProps } from "$fresh/server.ts"
import { API_HOST } from "../utils/api.ts"
import { MockPolls, MockQuestions } from "../utils/mock-data.ts"

interface Data {
  poll: Poll,
  questions: Question[]
}

export const handler: Handlers<Data> = {
  GET: async (req, ctx) => {
    try {
      throw Error("API not ready")
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
    catch (err) {
      console.error(err)

      const code = new URL(req.url).searchParams.get("code") as string
      const poll = MockPolls.find(p => p.code === parseInt(code))
      if (!poll) return ctx.renderNotFound()
      const questions = MockQuestions.filter(q => poll?.questionIds.includes(q.id))
      return ctx.render({
        poll,
        questions
      })
    }
  }
}

export default function VotePage({ data }: PageProps<Data>) {
  return (
    <App>
      <Head>
        <Style fileName="vote.css" />
      </Head>
      <main class="page box-bg">
        <VoteView {...data} />
      </main>
    </App>
  )
}