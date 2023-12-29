<!-- the "content-page" element is passed as a reusable component -->
<template id="contact-page">
  <div class="card m-10">
    <div class="card-body m-10">
      <h5 class="card-title text-center" style="color: blue">
        Contact us for more information
      </h5>
      <div class="toast card m-20" data-autohide="true" v-if="showToast">
        <div class="toast-header">
          <strong class="mr-auto text-success">Sent message</strong>
          <button type="button" class="ml-2 mb-1 close" data-dismiss="toast">
            &times;
          </button>
        </div>
        <div class="toast-body">
          Thanks for contacting us. Will get back to you in 24hrs
        </div>
      </div>
      <form action="" class="form-fields" @submit.prevent="sendEmail">
        <div class="field">
          <input
            type="email"
            v-model="formData.email"
            name="email"
            placeholder="Enter your Email"
          />
          <input type="text" v-model="formData.subject" placeholder="Subject" />
          <textarea
            type="textarea"
            v-model="formData.message"
            placeholder="Enter your message"
          ></textarea>
        </div>
        <div class="submitbutton text-center">
          <button type="submit" class="btn btn-info btn-simple">Submit</button>
        </div>
      </form>
    </div>
  </div>
</template>
<style>
.field {
  margin: 10px 10px;
}
input,
textarea {
  height: 50px;
  width: 100%;
  margin: 20px;
  border-top: none;
  border-right: none;
  border-left: none;
  outline: none;
}
textarea {
  height: 100px;
  border-bottom: 2px solid rgb(133, 133, 133);
}
submitbutton button {
  margin: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
<script>
app.component("contact-page", {
  template: "#contact-page",
  data: () => {
    return {
      formData: [],
      showToast: false,
    };
  },
  created() {},
  methods: {
    sendEmail: function () {
      const url = `/api/users/contact`;
      axios
        .post(url, {
          from: this.formData.email,
          subject: this.formData.subject,
          message: this.formData.message,
        })
        .then((response) => {
          console.log("Send email successfully");
          // Clear the form
          this.formData.email = "";
          this.formData.subject = "";
          this.formData.message = "";
          // Show the success toast
          this.showToast = true;
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
});
</script>
