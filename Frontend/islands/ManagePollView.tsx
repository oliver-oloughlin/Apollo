import { getUser } from "./AppState.tsx"
import { API_HOST } from "../utils/api.ts"
import { Poll, Question } from "../utils/models.ts"
import { useState, useEffect, useRef, useMemo } from "preact/hooks"

async function questionsFetcher(poll: Poll | null) {
  if (!poll) return []
  const qFetchers = poll.questionIds.map(id => fetch(`${API_HOST}/question/${id}`).then(res => res.json()))
  const questions = await Promise.all(qFetchers) as Question[] ?? []
  return questions
}

async function pollFetcher() {
  const user = await getUser()
  if (!user) window.open(`/sign-in?next=${location.href}`, "_self")
  const code = new URLSearchParams(location.search).get("code")
  const poll = await fetch(`${API_HOST}/poll/${code}`).then(res => res.json()) as Poll
  if (!poll) window.open("/user", "_self")
  return poll
}

export default function ManagePollView() {
  const [loading, setLoading] = useState<boolean>(true)
  const [poll, setPoll] = useState<Poll | null>(null)
  const [questions, setQuestions] = useState<Question[]>([])
  const titleRef = useRef<HTMLInputElement>(null)
  const dateRef = useRef<HTMLInputElement>(null)
  const timeRef = useRef<HTMLInputElement>(null)
  const privacyRef = useRef<HTMLInputElement>(null)
  const questionFormRef = useRef<HTMLFormElement>(null)
  const questionRef = useRef<HTMLInputElement>(null)

  const questionRows = useMemo(() => {
    return questions.map(q => {
      return (
        <div class="questions-row">
          <div class="questions-text">{q.text}</div>
          <div><button onClick={() => handleDeleteQuestion(q.id)} class="questions-delete-btn">DELETE</button></div>
        </div>
      )
    })
  }, [questions])

  useEffect(() => {
    pollFetcher()
      .then(setPoll)
      .then(() => setLoading(false))
  }, [])

  useEffect(() => {
    questionsFetcher(poll)
      .then(setQuestions)

    if (poll) {
      const [ date, time ] = poll?.timeToStop.split(" ")
      titleRef.current!.value = poll.title
      dateRef.current!.value = date
      timeRef.current!.value = time.substring(0, time.length - 3)
      privacyRef.current!.checked = poll.privatePoll
    }

  }, [poll])

  async function handleAddQuestion(e: Event) {
    e.preventDefault()

    const data: Partial<Question> = {
      text: questionRef.current?.value,
      pollCode: poll?.code
    }
    
    const res = await fetch(`${API_HOST}/question`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data)
    })

    if (res.ok) {
      await pollFetcher().then(setPoll)
      questionFormRef.current?.reset()
    }
  }

  async function handleDeleteQuestion(id: number) {
    const res = await fetch(`${API_HOST}/question/${id}`, {
      method: "DELETE"
    })

    if (res.ok) await pollFetcher().then(setPoll)
  }

  async function handleUpdatePoll(e: Event) {
    e.preventDefault()

    const date = dateRef.current!.value
    const time = timeRef.current!.value

    const data: Partial<Poll> = {
      code: poll!.code,
      closed: poll!.closed,
      privatePoll: privacyRef.current!.checked,
      title: titleRef.current!.value,
      timeToStop: `${date} ${time}:00`,
    }

    const res = await fetch(`${API_HOST}/poll`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data)
    })

    if (res.ok) res.json().then(setPoll)
  }

  if (loading) return <div>Loading...</div>

  return (
    <div class="page-content">
      <div class="centered-x poll-container">
        <h1 class="centered-text">Manage Poll Page</h1>
        <div>
          <h2>Poll Settings</h2>
          <form class="manage-poll-form" onSubmit={handleUpdatePoll} >
            <div>
              <strong>Code</strong>
              <div>{poll?.code}</div>
            </div>
            <div>
              <label for="poll-title"><strong>Title</strong></label>
              <input
                id="poll-title"
                name="poll-title"
                type="text"
                required
                ref={titleRef}
              />
            </div>
            <div>
              <label for="poll-date"><strong>End Date</strong></label>
              <input
                id="poll-date"
                name="poll-date"
                type="date"
                required
                ref={dateRef}
              />
            </div>
            <div>
              <label for="poll-time"><strong>Title</strong></label>
              <input
                id="poll-time"
                name="poll-time"
                type="time"
                required
                ref={timeRef}
              />
            </div>
            <span>
              <label for="poll-privacy"><strong>Private Poll</strong></label>
              <input
                id="poll-privacy"
                name="poll-privacy"
                type="checkbox"
                ref={privacyRef}
              />
            </span>
            <button type="submit">SAVE CHANGES</button>
          </form>
        </div>
        <div>
          <h2>Add New Question</h2>
          <form onSubmit={handleAddQuestion} ref={questionFormRef} class="manage-poll-form">
            <input
              type="text"
              placeholder="Question"
              required
              ref={questionRef}
            />
            <button type="submit">ADD QUESTION</button>
          </form>
        </div>
        <div>
          <h2>Questions</h2>
          <div class="questions-column">
            <div class="questions-row">
              <div class="questions-heading">Text</div>
            </div>
            {questionRows}
          </div>
        </div>
      </div>
    </div>
  )
}