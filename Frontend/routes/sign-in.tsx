/** @jsx h */
import { h } from "preact";
import App from "../components/App.tsx";
import { Head } from "$fresh/runtime.ts"
import Style from "../components/Style.tsx";

export default function Home() {
  return (
    <App>
      <Head>
        <Style fileName="form.css" />
      </Head>
      <main class="page">
        <form class="form">
          <input
            type="email"
            placeholder="Email"
            name="email"
            required
          />
          <input
            type="password"
            placeholder="Password"
            name="password"
            required
          />
          <button type="submit">SIGN IN</button>
          <p class="centered-text"><a href="/sign-in" class="link">Create an account</a></p>
        </form>
      </main>
    </App>
  );
}
