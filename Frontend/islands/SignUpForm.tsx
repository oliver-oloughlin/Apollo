import { Fragment } from "preact"
import { Head } from "$fresh/runtime.ts"
import { Style } from "fresh_utils"
import { useRef } from "preact/hooks"
import { AccountCredentials } from "../utils/models.ts"
import { encrypt } from "../utils/security.ts"
import { API_HOST } from "../utils/api.ts"
import { PasswordValidator, regExpToString } from "../utils/validation.ts"

export default function SignUpForm() {
  const emailRef = useRef<HTMLInputElement>(null)
  const passRef = useRef<HTMLInputElement>(null)
  const repeatRef = useRef<HTMLInputElement>(null)

  function addInvalidationShaker(elem: HTMLElement) {
    elem.classList.add("invalidation-shake")
  }

  async function handleSubmit(e: Event) {
    e.preventDefault()

    const data: AccountCredentials = {
      email: emailRef.current?.value,
      password: await encrypt(passRef.current!.value)
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
      else {
        const message = await res.text()
        alert(message)
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
          onClick={() => addInvalidationShaker(emailRef.current!)}
          ref={emailRef}
          required
        />
        <input
          type="password"
          placeholder="Password"
          name="password"
          onInput={checkPasswordsMatch}
          onClick={() => addInvalidationShaker(passRef.current!)}
          ref={passRef}
          pattern={regExpToString(PasswordValidator)}
          required
        />
        <input
          type="password"
          placeholder="Repeat Password"
          name="repeatPassword"
          onInput={checkPasswordsMatch}
          onClick={() => addInvalidationShaker(repeatRef.current!)}
          ref={repeatRef}
          required
        />
        <button type="submit">SIGN UP</button>
        <p class="centered-text"><a href="/sign-in" class="link">Already have an account?</a></p>
      </form>
    </Fragment>
  )
}
