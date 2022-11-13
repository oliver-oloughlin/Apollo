import { useEffect } from "preact/hooks"
import { signal } from "@preact/signals"
import { Account } from "../utils/models.ts"
import { API_HOST } from "../utils/api.ts"

const UserSignal = signal<Account | null>(null)

async function updateUser() {
  try {
    const res = await fetch(`${API_HOST}/account/${UserSignal.value?.email}`)
    if (!res.ok) UserSignal.value = null
    else UserSignal.value = await res.json()
  }
  catch (err) {
    console.error(err)
    UserSignal.value = null
  }
}

export async function getUser() {
  await updateUser()
  return UserSignal.value
}

export function setUser(user: Account | null) {
  UserSignal.value = user
}

export default function AppState() {
  useEffect(() => {
    const userStr = localStorage.getItem("user")
    const user = JSON.parse(userStr!) as Account | null
    UserSignal.value = user
    updateUser()
  }, [])

  useEffect(() => {
    const user = UserSignal.value
    if (user) localStorage.setItem("user", JSON.stringify(user))
    else localStorage.removeItem("user")
  }, [UserSignal.value])

  return <div style={{ display: "none" }}></div>
}