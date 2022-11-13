package controller;

import static spark.Spark.get;

public class PingController {

  public PingController() {
    get("/ping", (req, res) -> {
      res.status(200);
      return "Success";
    });
  }
}
