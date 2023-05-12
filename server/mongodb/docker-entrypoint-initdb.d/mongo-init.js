db.createUser({
  user: 'test',
  pwd: '1234',
  roles: [
    {
      role: 'dbOwner',
      db: 'marveldb',
    },
  ],
});
