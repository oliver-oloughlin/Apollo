import { useEffect, useState, useMemo, useRef } from "preact/hooks"
import { API_HOST } from "../utils/api.ts"
import { Poll } from "../utils/models.ts"
import { getUser, setUser } from "./AppState.tsx"

async function pollsFetcher() {
  try {
    const user = await getUser()
    if (!user) window.open("/sign-in?next=/user", "_self")
    const polls = await fetch(`${API_HOST}/poll`).then(res => res.json()) as Poll[]
    return polls.filter(poll => !poll.closed && poll.ownerEmail === user?.email)
  }
  catch (err) {
    console.error(err)
    return [] as Poll[]
  }
}

export default function UserView() {
  const [loading, setLoading] = useState<boolean>(true)
  const [polls, setPolls] = useState<Poll[] | null>(null)
  const formRef = useRef<HTMLFormElement>(null)
  const titleRef = useRef<HTMLInputElement>(null)
  const dateRef = useRef<HTMLInputElement>(null)
  const timeRef = useRef<HTMLInputElement>(null)
  const privacyRef = useRef<HTMLInputElement>(null)

  const pollRows = useMemo(() => {
    return polls?.map((poll, index) => {
      return (
        <div class="polls-row" key={`poll-${index}`} onClick={() => window.open(`/user/poll?code=${poll.code}`, "_self")}>
          <p class="polls-title">{poll.title}</p>
          <p>{poll.timeToStop}</p>
          <p>{poll.privatePoll ? "PRIVATE" : "PUBLIC"}</p>
        </div>
      )
    })
  }, [polls])

  useEffect(() => {
    pollsFetcher().then(polls => {
      setPolls(polls)
      setLoading(false)
    })
  }, [])
  
  async function handleSignOut() {
    const res = await fetch(`${API_HOST}/logout`)
    setUser(null)
    window.open("/", "_self")
  }

  async function handleCreatePoll(e: Event) {
    e.preventDefault()

    const user = await getUser()
    if (!user) window.open("/sign-in?next=/user", "_self")
    
    const title = titleRef.current!.value
    const date = dateRef.current!.value
    const time = timeRef.current!.value
    const privatePoll = privacyRef.current!.checked

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
      pollsFetcher().then(setPolls)
    }
  }

  if (loading) return <div>Loading...</div>

  return (
    <div class="page-content">
      <div class="user-container centered-x">
        <h1 class="centered-text">Account Page</h1>
        <a class="link" onClick={handleSignOut}>Sign Out</a>
        <div>
          <form class="create-poll-form" ref={formRef} onSubmit={handleCreatePoll}>
            <h2>Create New Poll</h2>
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
          <h2>My Polls</h2>
          <div class="polls-column">
            <div class="polls-row">
              <strong class="polls-heading">Title</strong>
              <strong class="polls-heading">Ends</strong>
              <strong class="polls-heading">Access</strong>
            </div>
            {pollRows}
          </div>
        </div>
      </div>
    </div>
  )
}