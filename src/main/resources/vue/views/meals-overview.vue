<template id="meals-overview">
  <app-layout>
    <div class="card bg-light mb-3">
      <div class="card-header">
        <div class="row">
          <div class="col-6">Meals</div>
          <div class="col" align="right">
            <button
              rel="tooltip"
              title="Add"
              class="btn btn-info btn-simple btn-link"
              @click="hideForm = !hideForm"
            >
              <i class="fa fa-plus" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
    </div>
    <div class="card-body" :class="{ 'd-none': hideForm }">
      <form id="addMeals">
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text">Description</span>
          </div>
          <input
            type="text"
            class="form-control"
            v-model="formData.description"
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
            v-model="formData.time"
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
            v-model="formData.calories"
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
            v-model="formData.userId"
            name="userId"
            placeholder="userId"
          />
        </div>
      </form>
      <button
        rel="tooltip"
        title="Update"
        class="btn btn-info btn-simple btn-link"
        @click="addMeals()"
      >
        Add Meals
      </button>
    </div>
    <div class="list-group list-group-flush meals-overview-list">
      <div
        class="list-group-item d-flex align-items-start"
        v-for="(meal, index) in meals"
        v-bind:key="index"
      >
        <div class="mr-auto p-2">
          <span
            ><a :href="`/meals/${meal.id}`">
              {{ meal.description }} ({{ meal.calories }})</a
            ></span
          >
        </div>
        <div class="p2">
          <a :href="`/meals/${meal.id}`">
            <button
              rel="tooltip"
              title="Update"
              class="btn btn-info btn-simple btn-link"
            >
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </button>
          </a>
          <button
            rel="tooltip"
            title="Delete"
            class="btn btn-info btn-simple btn-link"
            @click="deleteMeals(meal, index)"
          >
            <i class="fas fa-trash" aria-hidden="true"></i>
          </button>
        </div>
      </div>
    </div>
  </app-layout>
</template>
<script>
app.component("meals-overview", {
  template: "#meals-overview",
  data: () => ({
    meals: [],
    formData: [],
    hideForm: true,
  }),
  created() {
    this.fetchMeals();
  },
  methods: {
    fetchMeals: function () {
      axios
        .get("/api/meals")
        .then((res) => {
          this.meals = res.data;
          console.log(res.data);
        })
        .catch(() => alert("Error while fetching meals"));
    },
    deleteMeals: function (meal, index) {
      if (
        confirm(
          "Are you sure you want to delete this meal? This action cannot be undone.",
          "Warning",
        )
      ) {
        //confirmed delete
        const mealId = meal.id;
        const url = `/api/meals/${mealId}`;
        axios
          .delete(url)
          .then((response) =>
            //delete from the local state so Vue will reload list automatically
            this.meals.splice(index, 1).push(response.data),
          )
          .catch(function (error) {
            console.log(error);
          });
      }
    },
    addMeals: function () {
      const url = `/api/meals`;

      axios
        .post(url, {
          description: this.formData.description,
          time: this.formData.time,
          calories: this.formData.calories,
          userId: this.formData.userId,
        })
        .then((response) => {
          this.meals.push(response.data);
          this.hideForm = true;
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
});
</script>
