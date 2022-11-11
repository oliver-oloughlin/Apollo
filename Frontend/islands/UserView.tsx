import { useEffect, useState, useMemo, useRef } from "preact/hooks"
import { API_HOST } from "../utils/api.ts"
import { Account, Poll } from "../utils/models.ts"

async function pollsFetcher() {
  try {
    return await fetch(`${API_HOST}/poll`)
      .then(res => res.json()) as Poll[]
  }
  catch (err) {
    console.error(err)
    return [] as Poll[]
  }
}

export default function UserView() {
  const [loading, setLoading] = useState<boolean>(true)
  const [user, setUser] = useState<Account | null>(null)
  const [polls, setPolls] = useState<Poll[] | null>(null)
  const formRef = useRef<HTMLFormElement>(null)
  const titleRef = useRef<HTMLInputElement>(null)
  const dateRef = useRef<HTMLInputElement>(null)
  const timeRef = useRef<HTMLInputElement>(null)
  const privacyRef = useRef<HTMLInputElement>(null)

  const pollsList = useMemo(() => {
    return (
      <ul>
        <li>
          <p class="poll-title-heading">Title</p>
          <p class="poll-time-heading">Time</p>
          <p class="poll-privacy-heading">Privacy</p>
        </li>
        {polls?.map(p => {
          return (
            <li>
              <p class="poll-title">{p.title}</p>
              <p class="poll-time">{p.timeToStop}</p>
              <p class="poll-privacy">{p.privatePoll}</p>
            </li>
          )
        })}
      </ul>
    )
  }, [polls])

  useEffect(() => {
    const userStr = localStorage.getItem("user")

    if (!userStr) {
      window.open("/sign-in?next=/user", "_self")
      return
    }

    const _user = JSON.parse(userStr) as Account
    pollsFetcher()
      .then(_polls => {
        setUser(_user)
        setPolls(_polls.filter(p => p.ownerEmail === _user.email))
        setLoading(false)
      })
  }, [])
  
  async function handleSignOut() {
    const res = await fetch(`${API_HOST}/logout`)
    localStorage.removeItem("user")
    setUser(null)
    window.open("/", "_self")
  }

  async function handleCreatePoll(e: Event) {
    e.preventDefault()
    
    const title = titleRef.current!.value
    const date = dateRef.current!.value
    const time = timeRef.current!.value
    const privatePoll = privacyRef.current!.value === "private"

    const data: Partial<Poll> = {
      title,
      timeToStop: `${date} ${time}:00`,
      ownerEmail: user?.email,
      code: Math.round(Math.random() * 1_000_000_000),
      privatePoll
    }

    const res = await fetch(`${API_HOST}/poll`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "mode": "cors"
      },
      body: JSON.stringify(data)
    })

    if (res.ok) {
      formRef.current!.reset()
      pollsFetcher()
    }
  }

  if (loading) return <div>Loading...</div>

  return (
    <div class="page-content">
      <div class="user-container text-container centered-x">
        <h1 class="centered-text">Account Page</h1>
        <button onClick={handleSignOut}>SIGN OUT</button>
        <div>
          <h2>Create New Poll</h2>
          <form class="create-poll-form" ref={formRef} onSubmit={handleCreatePoll}>
            <input
              type="text"
              name="title"
              placeholder="Title"
              required
              ref={titleRef}
            />
            <input
              type="date"
              name="date"
              value="2022-12-31"
              required
              ref={dateRef}
            />
            <input
              type="time"
              name="time"
              value="23:59"
              required
              ref={timeRef}
            />
            <span>
              <label for="privacy">Private Poll</label>
              <input
                id="privacy"
                type="checkbox"
                name="privacy"
                value="private"
                ref={privacyRef}
              />
            </span>
            <button type="submit">SUBMIT</button>
          </form>
        </div>
        <div>
          
        </div>
      </div>
    </div>
  )
}