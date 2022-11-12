import App from "../../components/App.tsx"
import ManagePollView from "../../islands/ManagePollView.tsx"
import { Head } from "$fresh/runtime.ts"
import { Style } from "fresh_utils"

export default function ManagePollPage() {
  return (
    <App>
      <Head>
        <Style fileName="poll.css" />
      </Head>
      <main>
        <ManagePollView />
      </main>
    </App>
  )
}