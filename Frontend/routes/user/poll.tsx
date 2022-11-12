import App from "../../components/App.tsx"
import ManagePollView from "../../islands/ManagePollView.tsx"

export default function ManagePollPage() {
  return (
    <App>
      <main>
        <ManagePollView />
      </main>
    </App>
  )
}