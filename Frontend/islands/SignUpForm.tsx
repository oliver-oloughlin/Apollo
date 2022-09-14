/** @jsx h */
import { h, Fragment } from "preact";
import { Head } from "$fresh/runtime.ts"
import Style from "../components/Style.tsx";

export default function SignUpForm() {
  const handleSubmit = async (e: Event) => {
    e.preventDefault()
  }

  return (
    <Fragment>
      <Head>
        <Style fileName="form.css" />
      </Head>
      <form onSubmit={handleSubmit} class="form">
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
        <input
          type="password"
          placeholder="Repeat Password"
          name="repeatPassword"
          required
        />
        <button type="submit">SIGN UP</button>
        <p class="centered-text"><a href="/sign-in" class="link">Already have an account?</a></p>
      </form>
    </Fragment>
  );
}
