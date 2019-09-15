function fn() {
  config = {
    INSTANCE_URL: 'http://localhost:65535'
  };
  karate.configure('connectTimeout', 5000);
  karate.configure('readTimeout', 5000);
  return config;
}