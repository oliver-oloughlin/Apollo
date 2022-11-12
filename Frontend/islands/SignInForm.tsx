import { Fragment } from "preact"
import { useRef } from "preact/hooks"
import { Head } from "$fresh/runtime.ts"
import { Style } from "fresh_utils"
import { AccountCredentials, Account } from "../utils/models.ts"
import { encrypt } from "../utils/security.ts"
import { API_HOST } from "../utils/api.ts"
import { setUser } from "./AppState.tsx"

export default function SignInForm() {
  const emailRef = useRef<HTMLInputElement>(null)
  const passRef = useRef<HTMLInputElement>(null)

  async function handleSubmit(e: Event) {
    e.preventDefault()

    const data: AccountCredentials = {
      email: emailRef.current!.value,
      password: await encrypt(passRef.current!.value)
    }
    
    try {
      const res = await fetch(`${API_HOST}/login`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
      })
  
      if (res.ok) {
        const account = await res.json() as Account
        setUser(account)
        const next = new URLSearchParams(location.search).get("next")
        //if (next) window.open(next, "_self")
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
  )
}
