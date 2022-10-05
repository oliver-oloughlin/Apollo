
import App from "../components/App.tsx"
import { Head } from "$fresh/runtime.ts"
import { Style } from "fresh_utils"
import SignUpForm from "../islands/SignUpForm.tsx"

export default function Home() {
  return (
    <App>
      <Head>
        <Style fileName="form.css" />
      </Head>
      <main class="page">
        <SignUpForm />
      </main>
    </App>
  );
}
