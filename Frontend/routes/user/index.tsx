import App from "../../components/App.tsx"
import UserView from "../../islands/UserView.tsx"
import { Head } from "$fresh/runtime.ts"
import { Style } from "fresh_utils"

export default function Home() {
  return (
    <App>
      <Head>
        <Style fileName="user.css" />
        <Style fileName="polls.css" />
      </Head>
      <main>
        <UserView />
      </main>
    </App>
  )
}
