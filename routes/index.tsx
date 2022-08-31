/** @jsx h */
import { h } from "preact";
import App from "../components/App.tsx"
import Style from "../components/Style.tsx"
import { Head } from "$fresh/runtime.ts"

export default function Home() {
  return (
    <App>
      <Head>
        <Style fileName="buttons.css" />
      </Head>
      <main class="page">
        <button class="btn-red btn-big">RED</button>
        <button class="btn-blue btn-big">BLUE</button>
      </main>
    </App>
  );
}
