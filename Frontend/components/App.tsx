/** @jsx h */
import { Fragment, h, JSX } from "preact";
import { Head } from "$fresh/runtime.ts";
import Style from "./Style.tsx";

export default function App({ children }: { children?: any }) {
  return (
    <Fragment>
      <Head>
        <Styles />
      </Head>
      <Layout>
        {children}
      </Layout>
    </Fragment>
  );
}

function Styles() {
  return (
    <Fragment>
      <Style fileName="globals.css" />
      <Style fileName="view.css" />
    </Fragment>
  );
}

function Layout({ children }: { children?: JSX.Element }) {
  return <div class="layout">{children}</div>;
}
