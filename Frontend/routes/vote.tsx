import App from "../components/App.tsx"
import VoteView from "../islands/VoteView.tsx"
import { Style } from "fresh_utils"
import { Head } from "$fresh/runtime.ts"

export default function VotePage() {
  return (
    <App>
      <Head>
        <Style fileName="vote.css" />
      </Head>
      <main class="page box-bg">
        <VoteView />
      </main>
    </App>
  )
}