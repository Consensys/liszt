<template id="transfer-form">
  <div>
    <b-form @submit="onSubmit" @reset="onReset">
    <b-form-row>
      <b-col cols="8">
        <b-form-group label="From">
          <b-form-input v-model="form.from" required placeholder="Public Key">
        </b-form-input>
      </b-form-group>
      </b-col>

      <b-col>
         <b-form-group label="Rollup Id">
             <b-form-input  :disabled="true" v-model="rIdName" required placeholder="Source Rollup Id" />
          </b-form-group>
      </b-col>
     </b-form-row>


     <b-form-row>
       <b-col cols="8">
         <b-form-group id="input-group-2" label="To" label-for="input-2">
           <b-form-input id="input-2" v-model="form.to" required placeholder="Public Key"></b-form-input>

          </b-form-group>
       </b-col>

         <b-col>
           <b-form-group id="input-group-rollupId2" label="Target Rollup Id" label-for="input-9">
           <b-form-select v-model="selected" :options="options" required placeholder="Rollup Id"></b-form-select>

           </b-form-group>
         </b-col>

      </b-form-row>


      </b-form-group>

      <b-form-row>
        <b-col b-col cols="8"><b-form-input v-model="form.hashOfThePendingTransfer"  placeholder="Pending Transfer" /></b-col>

        <b-col><b-form-input v-model="form.amount" required placeholder="Amount" /></b-col>

        <b-col v-if = "this.form.rIdFrom!=this.selected"> <b-form-input required placeholder="Timeout">
            {{"form.timeout"}}
        </b-form-input></b-col>

        <b-col><b-form-input v-model="form.nonce" required placeholder="Nonce"/></b-col>
      </b-form-row>


      <b-form-row>

       <b-button class="mr-2" type="submit" variant="primary">Submit</b-button>
       <b-button type="reset" variant="danger">Reset</b-button>

       </b-form-row>
    </b-form>

  </div>
</template>

<script>
  export default {
    props: {
      transfer: Object,
      rIdName: String,
    },

    computed: {
       form: {
          get: function(){
                return this.transfer; //return Object.assign({}, this.transfer);
           },

           set: function(newValue){
           }
       },
     },

     data() {
           return {
               selected: this.transfer.rIdFrom,
                options: [
                         { value: 0, text: 'Rollup A' },
                         { value: 1, text: 'Rollup B' },
                 ]
           }
       },

    methods: {
      onSubmit(evt) {
        evt.preventDefault()
        this.form.nonce = this.form.nonce +1
        this.form.timeout = 100
        this.form.rIdTo = this.selected
        this.$emit('add:transfer', this.form)
        this.selected = this.form.rIdFrom
      },

      onReset(evt) {
        evt.preventDefault()
        // Reset our form values
        this.form.from = ''
        this.form.to= ''
        this.form.rIdTo=''
        this.form.amount=''
        //this.form.nonce=''
        this.form.hashOfThePendingTransfer=''
      }
    }
  }
</script>