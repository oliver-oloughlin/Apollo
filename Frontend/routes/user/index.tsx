/** @jsx h */
import { h } from "preact";
import App from "../../components/App.tsx";
import { Handlers } from "$fresh/server.ts"
import { getCookies } from "http/cookie.ts"
import { redirect } from "../../utils/api.ts";

export const handler: Handlers = {
  GET: (req, ctx) => {
    const { auth } = getCookies(req.headers)
    if (!auth) return redirect("/sign-in")
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
  );
}
