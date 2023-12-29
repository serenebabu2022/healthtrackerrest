<template id="meal-details">
  <app-layout>
    <div v-if="noMealFound">
      <p>We're sorry, we were not able to retrieve this meal.</p>
      <p>View <a :href="'/meals'">all meals</a>.</p>
    </div>
    <div>
      <div class="card bg-light mb-3" v-if="!noMealFound">
        <div class="card-header">
          <div class="row">
            <div class="col-6">Meals Details</div>
            <div class="col" align="right">
              <button
                rel="tooltip"
                title="Update"
                class="btn btn-info btn-simple btn-link"
                @click="updateMeal()"
              >
                <i class="far fa-save" aria-hidden="true"></i>
              </button>
              <button
                rel="tooltip"
                title="Delete"
                class="btn btn-info btn-simple btn-link"
                @click="deleteMeal()"
              >
                <i class="fas fa-trash" aria-hidden="true"></i>
              </button>
            </div>
          </div>
        </div>
        <div class="card-body">
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">Meal ID</span>
            </div>
            <input
              type="number"
              class="form-control"
              v-model="meals.id"
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
              v-model="meals.description"
              name="description"
              placeholder="Description"
            />
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">Time</span>
            </div>
            <input
              type="text"
              class="form-control"
              v-model="meals.time"
              name="time"
              placeholder="Time"
            />
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">Calories</span>
            </div>
            <input
              type="text"
              class="form-control"
              v-model="meals.calories"
              name="calories"
              placeholder="Calories"
            />
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">userId</span>
            </div>
            <input
              type="text"
              class="form-control"
              v-model="meals.userId"
              name="userId"
              placeholder="userId"
            />
          </div>
        </div>
      </div></div
  ></app-layout>
</template>
<script>
app.component("meal-details", {
  template: "#meal-details",
  data: () => ({
    noMealFound: false,
    meals: null,
  }),
  created: function () {
    const mealId = this.$javalin.pathParams["meal-id"];
    const url = `/api/meals/${mealId}`;
    axios
      .get(url)
      .then((res) => {
        this.meals = res.data;
      })
      .catch((error) => {
        console.log(
          "No meal found for id passed in the path parameter: " + error,
        );
        this.noMealFound = true;
      });
  },
  methods: {
    updateMeal: function () {
      const mealId = this.$javalin.pathParams["meal-id"];
      const url = `/api/meals/${mealId}`;
      axios
        .patch(url, {
          description: this.meals.description,
          time: this.meals.time,
          calories: this.meals.calories,
          userId: this.meals.userId,
        })
        .then((response) => this.meals.push(response.data))
        .catch((error) => {
          console.log(error);
        });
      alert("Meals updated!");
    },
    deleteMeal: function () {
      if (
        confirm(
          "Are you sure you want to delete this meal? This action cannot be undone.",
          "Warning",
        )
      ) {
        //confirmed delete
        const mealId = this.$javalin.pathParams["meal-id"];
        const url = `/api/meals/${mealId}`;
        axios
          .delete(url)
          .then((response) => {
            alert("Meal deleted");
            window.location.href = "/meals";
          })
          .catch(function (error) {
            console.log(error);
          });
      }
    },
  },
});
</script>
