import App from "../components/App.tsx"
import { Handlers, PageProps } from "$fresh/server.ts"
import { Poll } from "../utils/models.ts"
import PollsView from "../islands/PollsView.tsx"
import { API_HOST } from "../utils/api.ts"

interface Data {
  polls: Poll[]
}

export const handler: Handlers<Data> = {
  GET: async (_req, ctx) => {
    try {
      const res = await fetch(`${API_HOST}/poll`)
      const polls = await res.json() as Poll[] ?? [] as Poll[]
      return ctx.render({ polls })
    } catch(err) {
      console.error(err)
      return ctx.render({ polls: []})
    }
  }
}

export default function Home({ data }: PageProps<Data>) {
  const { polls } = data
  return (
    <App>
      <main class="page">
        <div class="page-content">
          <PollsView polls={polls} />
        </div>
      </main>
    </App>
  )
}
