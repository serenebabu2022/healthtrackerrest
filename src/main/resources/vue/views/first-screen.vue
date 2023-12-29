<!-- the "home-page" element is passed as a parameter to VueComponent in the JavalinConfig file -->
<template id="first-page">
  <app-layout>
    <div class="row">
      <div class="col">
        <div class="card border-primary mb-3">
          <div class="card-body">
            <h5 class="card-title text-primary">
              <i class="fa-solid fa-dumbbell"></i
              ><span class="ml-2">Training</span>
            </h5>
            <p class="card-text">Create a training routine for the users</p>
            <a href="/activities" class="btn btn-primary">Try out</a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card border-primary mb-3">
          <div class="card-body">
            <h5 class="card-title text-primary">
              <i class="fa-solid fa-bowl-food" aria-hidden="true"></i
              ><span class="ml-2">Diet</span>
            </h5>
            <p class="card-text">Build a nutrition plan for the users</p>
            <a href="/meals" class="btn btn-primary">Try out</a>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col">
        <div class="card border-primary mb-3">
          <div class="card-body">
            <h5 class="card-title text-primary">
              <i class="fa-solid fa-users"></i><span class="ml-2">Users</span>
            </h5>
            <p class="card-text">See all users using the app</p>
            <a href="/users" class="btn btn-primary">Connect</a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card border-primary mb-3">
          <div class="card-body">
            <h5 class="card-title text-primary">
              <i class="fa-solid fa-arrow-up-right-dots"></i
              ><span class="ml-2">Progress</span>
            </h5>
            <p class="card-text">See the progress of users</p>
            <a href="/dashboard" class="btn btn-primary">See progress</a>
          </div>
        </div>
      </div>
    </div>
    <contact-page />
  </app-layout>
</template>

<script>
app.component("first-page", {
  template: "#first-page",
});
</script>
