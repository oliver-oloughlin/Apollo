import App from "../../components/App.tsx"
import { Handlers } from "$fresh/server.ts"
import { redirect } from "fresh_utils"
import { UserSignal } from "../../utils/state.ts"

export const handler: Handlers = {
  GET: (_req, ctx) => {
    console.log("SERVER: ", UserSignal.value)
    if (!UserSignal.value) return redirect("/sign-in?next=/user")
    return ctx.render()
  }
}

export default function Home() {
  return (
    <App>
      <main>
        Account Page
      </main>
    </App>
  )
}
