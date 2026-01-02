db = db.getSiblingDB("avengers-game");

db.createUser({
  user: process.env.MONGO_USER,
  pwd: process.env.MONGO_PASSWORD,
  roles: [
    {
      role: "readWrite",
      db: "avengers-game"
    }
  ]
});