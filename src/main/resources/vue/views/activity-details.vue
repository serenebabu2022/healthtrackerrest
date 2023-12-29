<template id="activity-details">
  <app-layout>
    <div v-if="noActivityFound">
      <p>We're sorry, we were not able to retrieve this activity.</p>
      <p>View <a :href="'/activities'">all activities</a>.</p>
    </div>
    <div>
      <div class="card bg-light mb-3" v-if="!noActivityFound">
        <div class="card-header">
          <div class="row">
            <div class="col-6">Activity Details</div>
            <div class="col" align="right">
              <button
                rel="tooltip"
                title="Update"
                class="btn btn-info btn-simple btn-link"
                @click="updateActivity()"
              >
                <i class="far fa-save" aria-hidden="true"></i>
              </button>
              <button
                rel="tooltip"
                title="Delete"
                class="btn btn-info btn-simple btn-link"
                @click="deleteActivity()"
              >
                <i class="fas fa-trash" aria-hidden="true"></i>
              </button>
            </div>
          </div>
        </div>
        <div class="card-body">
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">Activity ID</span>
            </div>
            <input
              type="number"
              class="form-control"
              v-model="activities.id"
              name="id"
              readonly
              placeholder="Id"
            />
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">description</span>
            </div>
            <input
              type="text"
              class="form-control"
              v-model="activities.description"
              name="description"
              placeholder="Description"
            />
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">Duration</span>
            </div>
            <input
              type="text"
              class="form-control"
              v-model="activities.duration"
              name="duration"
              placeholder="Duration"
            />
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">Calories</span>
            </div>
            <input
              type="text"
              class="form-control"
              v-model="activities.calories"
              name="calories"
              placeholder="Calories"
            />
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">Started</span>
            </div>
            <input
              type="text"
              class="form-control"
              v-model="activities.started"
              name="started"
              placeholder="Started"
            />
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">userId</span>
            </div>
            <input
              type="text"
              class="form-control"
              v-model="activities.userId"
              name="userId"
              placeholder="userId"
            />
          </div>
        </div>
      </div></div
  ></app-layout>
</template>
<script>
app.component("activity-details", {
  template: "#activity-details",
  data: () => ({
    noActivityFound: false,
    activities: null,
  }),
  created: function () {
    const activityId = this.$javalin.pathParams["activity-id"];
    const url = `/api/activities/${activityId}`;
    axios
      .get(url)
      .then((res) => {
        this.activities = res.data;
        console.log(this.activities);
      })
      .catch((error) => {
        console.log(
          "No activity found for id passed in the path parameter: " + error,
        );
        this.noActivityFound = true;
      });
  },
  methods: {
    updateActivity: function () {
      const activityId = this.$javalin.pathParams["activity-id"];
      const url = `/api/activities/${activityId}`;
      axios
        .patch(url, {
          description: this.activities.description,
          duration: this.activities.duration,
          calories: this.activities.calories,
          started: this.activities.started,
          userId: this.activities.userId,
        })
        .then((response) => this.activities.push(response.data))
        .catch((error) => {
          console.log(error);
        });
      alert("Activity updated!");
    },
    deleteActivity: function () {
      if (
        confirm(
          "Are you sure you want to delete this activity? This action cannot be undone.",
          "Warning",
        )
      ) {
        //confirmed delete
        const activityId = this.$javalin.pathParams["activity-id"];
        const url = `/api/activities/${activityId}`;
        axios
          .delete(url)
          .then((response) => {
            alert("Activity deleted");
            window.location.href = "/activities";
          })
          .catch(function (error) {
            console.log(error);
          });
      }
    },
  },
});
</script>
