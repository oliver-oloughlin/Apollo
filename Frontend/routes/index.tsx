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
      <main class="page box-bg">
        <div class="btn-switch">
          <button class="btn-red btn-round btn-big">NO</button>
          <button class="btn-blue btn-round btn-big">YES</button>
        </div>
      </main>
    </App>
  );
}
