
import App from "../components/App.tsx"
import { Style } from "fresh_utils"
import { Head } from "$fresh/runtime.ts"
import type { Poll } from "../utils/models.ts"
import { Handlers, PageProps } from "$fresh/server.ts"

/* export const handler: Handlers<Poll> = {
  GET: async (req, ctx) => {
    try {
      const id = new URL(req.url).searchParams.get("id") ?? ""
      const res = await fetch(`[backend api url]?id=${id}`)
      const poll = await res.json() as Poll
      // TODO - Change to: if (!poll) return ctx.renderNotFound()
      //        when Fresh is updated
      if (!poll) throw Error("Poll not found")
      return ctx.render(poll);
    } catch(err) {
      console.error(err)
      return new Response("Poll not found", { status: 404 });
    }
  }
} */

export default function Poll({ data }: PageProps<Poll>) {
  return (
    <App>
      <Head>
        <Style fileName="vote.css" />
      </Head>
      <main class="page box-bg">
        <div class="poll-container">
          <div class="question-container centered-text">
            <p>Some sort of binary question?</p>
          </div>
          <div class="switch">
            <button class="btn-red switch-btn" />
            <button class="btn-blue switch-btn" />
          </div>
        </div>
      </main>
    </App>
  );
}