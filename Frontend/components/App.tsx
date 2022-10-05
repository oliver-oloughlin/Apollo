import { Fragment, JSX } from "preact"
import { Head } from "$fresh/runtime.ts"
import { Style } from "fresh_utils"
import Header from "../islands/Header.tsx"

export default function App({ children }: { children?: any }) {
  return (
    <Fragment>
      <Head>
        <Style fileName="globals.css" />
        <Style fileName="view.css" />
        <Style fileName="header.css" />
      </Head>
      <Header />
      <Layout>
        {children}
      </Layout>
    </Fragment>
  );
}

function Layout({ children }: { children?: JSX.Element }) {
  return <div class="layout">{children}</div>;
}
