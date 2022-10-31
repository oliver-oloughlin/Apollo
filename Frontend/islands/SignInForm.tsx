import { Fragment } from "preact"
import { useRef } from "preact/hooks"
import { Head } from "$fresh/runtime.ts"
import { Style } from "fresh_utils"
import { AccountCredentials } from "../utils/models.ts"
import { encrypt } from "../utils/security.ts"
import { API_HOST } from "../utils/api.ts"

export default function SignInForm() {
  const emailRef = useRef<HTMLInputElement>(null)
  const passRef = useRef<HTMLInputElement>(null)

  const handleSubmit = async (e: Event) => {
    e.preventDefault()

    const data: AccountCredentials = {
      email: emailRef.current!.value,
      password: await encrypt(passRef.current!.value)
    }
    
    try {
      const res = await fetch(`${API_HOST}/sign-in`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
      })
  
      if (res.ok) {
        const next = new URL(location.origin).searchParams.get("next")
        if (next) window.open(next)
      }
    } catch(err) {
      console.error(err)
    }
  }

  return (
    <Fragment>
      <Head>
        <Style fileName="form.css" />
      </Head>
      <form onSubmit={handleSubmit} class="form">
        <p class="error-msg"></p>
        <p class="success-msg"></p>
        <input
          type="email"
          placeholder="Email"
          name="email"
          required
          ref={emailRef}
        />
        <input
          type="password"
          placeholder="Password"
          name="password"
          required
          ref={passRef}
        />
        <button type="submit">SIGN IN</button>
        <p class="centered-text"><a href="/sign-up" class="link">Create new account</a></p>
      </form>
    </Fragment>
  );
}
