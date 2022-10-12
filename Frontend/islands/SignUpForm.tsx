
import { Fragment } from "preact"
import { Head } from "$fresh/runtime.ts"
import { Style } from "fresh_utils"
import { useRef } from "preact/hooks"
import { SignUpData } from "../utils/models.ts"
import { API_HOST, encrypt } from "../utils/api.ts"
import { hashSync, genSaltSync } from "bcrypt"

export default function SignUpForm() {
  const emailRef = useRef<HTMLInputElement>(null)
  const passRef = useRef<HTMLInputElement>(null)
  const repeatRef = useRef<HTMLInputElement>(null)

  async function handleSubmit(e: Event) {
    e.preventDefault()

    const passwordHash = await encrypt(passRef.current!.value)
    const data: SignUpData = {
      email: emailRef.current?.value,
      password: hashSync(passwordHash, genSaltSync(10)),
      accountType: "Normal"
    }

    try {
      const res = await fetch(`${API_HOST}/account`, {
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

  function checkPasswordsMatch() {
    const repeatElem = repeatRef.current!
    const matches = repeatElem.value === passRef.current!.value
    if (matches) repeatElem.setCustomValidity("")
    else repeatElem.setCustomValidity("Passwords do not match")
  }

  return (
    <Fragment>
      <Head>
        <Style fileName="form.css" />
      </Head>
      <form onSubmit={handleSubmit} class="form validation-form">
        <input
          type="email"
          placeholder="Email"
          name="email"
          ref={emailRef}
          required
        />
        <input
          type="password"
          placeholder="Password"
          name="password"
          onChange={checkPasswordsMatch}
          ref={passRef}
          required
        />
        <input
          type="password"
          placeholder="Repeat Password"
          name="repeatPassword"
          onChange={checkPasswordsMatch}
          ref={repeatRef}
          required
        />
        <button type="submit">SIGN UP</button>
        <p class="centered-text"><a href="/sign-in" class="link">Already have an account?</a></p>
      </form>
    </Fragment>
  )
}
