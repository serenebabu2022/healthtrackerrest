<template id="login-view">
  <app-layout>
    <div class="card">
      <div class="card-body m-20">
        <h5 class="card-title text-primary">Login</h5>
        <form name="login-form">
          <div>
            <div class="mb-3">
              <label for="username">Email: </label>
              <input id="username" type="email" />
            </div>
            <div class="mb-3">
              <label for="password">Password: </label>
              <input id="password" type="password" />
            </div>
            <button class="btn btn-primary">Login</button>
          </div>
        </form>
      </div>
    </div></app-layout
  >
</template>

<script>
app.component("login-view", {
  template: "#login-view",
});
</script>
