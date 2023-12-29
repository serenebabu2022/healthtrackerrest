<template id="dashboard">
  <app-layout>
    <div class="container">
      <h2>Progress Chart</h2>
      <div>
        <canvas id="myChart"></canvas>
      </div>
    </div>
  </app-layout>
</template>
<style>
.container {
  width: 70%;
  margin: 15px auto;
}

body {
  text-align: center;
  color: #2e60e0;
}

h2 {
  text-align: center;
  font-family: "Verdana", sans-serif;
  font-size: 30px;
}
</style>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script
  src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js"
  type="text/javascript"
></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.2.2/Chart.min.js"></script>
<script>
app.component("dashboard", {
  template: "#dashboard",
  data: () => ({
    activities: [],
  }),
  created() {
    var activitiesList = [];
    axios
      .get("api/activities")
      .then((res) => {
        this.activities = res.data;
        activitiesList = JSON.parse(JSON.stringify(this.activities));
        this.fetchProgressChart(activitiesList);
      })
      .catch((error) => {
        console.log("Error fetching activities " + error);
      });
  },
  methods: {
    fetchProgressChart: function (activities) {
      const daysToMap = [];
      const duration = [];
      let displayDates;
      let displayDuration;
      activities.forEach((it) => {
        const dayOfWeek = new Date(it.started);
        duration.push(it.duration);
        const formattedDate = dayOfWeek.toLocaleDateString();
        daysToMap.push(formattedDate);
      });
      displayDates = daysToMap.reverse();
      displayDuration = duration.reverse();
      console.log(activities);

      let ctx = document.getElementById("myChart").getContext("2d");
      let myChart = new Chart(ctx, {
        type: "line",
        data: {
          labels: displayDates,
          datasets: [
            // {
            //   label: null,
            //   data: displayDates,
            //   type: "line",
            //   // backgroundColor: "rgba(153,205,1,0.6)",
            // },
            {
              label: "Activity Duration in Hours",
              data: displayDuration,
              type: "line",
              backgroundColor: "rgba(19,109,170,0.6)",
            },
          ],
        },
      });
    },
  },
});
</script>
