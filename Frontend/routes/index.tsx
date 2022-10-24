import App from "../components/App.tsx"
import PollsView from "../islands/PollsView.tsx"

export default function Home() {
  return (
    <App>
      <main class="page">
        <div class="page-content">
          <PollsView />
        </div>
      </main>
    </App>
  )
}
