db = db.getSiblingDB("avengers-game");

db.createUser({
  user: "app_user",
  pwd: "Mdb12345",
  roles: [
    {
      role: "readWrite",
      db: "avengers-game"
    }
  ]
});