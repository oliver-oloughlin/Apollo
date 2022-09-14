/** @jsx h */
import { h, Fragment } from "preact";
import { useRef } from "preact/hooks"
import { Head } from "$fresh/runtime.ts"
import Style from "../components/Style.tsx";

export default function SignInForm() {
  const formRef = useRef<HTMLFormElement>(null)
  const errorRef = useRef<HTMLParagraphElement>(null)

  const handleSubmit = async (e: Event) => {
    e.preventDefault()
    const form = formRef.current!
    const formData = new FormData(form)
    const data = Object.fromEntries(formData.entries())
    const res = await fetch("/some-api-endpoint", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data)
    })
    if (res.ok) {
      const params = new URL(window.location.search).searchParams
      const next = params.get("next")
      next ? window.open(next, "_self") : window.open("/user", "_self")
    } else {
      errorRef.current!.innerText = "Wrong email or password"
    }
  }

  return (
    <Fragment>
      <Head>
        <Style fileName="form.css" />
      </Head>
      <form ref={formRef} onSubmit={handleSubmit} class="form">
        <p class="error-msg" ref={errorRef}></p>
        <p class="success-msg"></p>
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
        <p class="centered-text"><a href="/sign-in" class="link">Create new account</a></p>
      </form>
    </Fragment>
  );
}
