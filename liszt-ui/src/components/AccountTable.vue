<template id="account-table">
  <div>
    <b-table :items="accounts" :fields="fields" striped sticky-header ="300px" bordered borderless head-variant	="dark" responsive="sm">

      <template slot="[index]" slot-scope="data">
        {{ data.index + 1 }}
      </template>

       <template slot="[owner]" slot-scope="data">
         <b-row class="mb-2">

          <b-col> {{data.item.publicKey | truncate(15)}} </b-col>
          <b-col> <b-button size="sm" @click="$emit('load:data', data.index)" class="mr-2"> copy address </b-button> </b-col>
        </b-row>

       </template>

      <template slot="[show_details]" slot-scope="data">
        <b-button size="sm" @click="data.toggleDetails" class="mr-2">
          {{ data.detailsShowing ? 'Hide' : 'Show'}} Details
        </b-button>
      </template>

      <template slot="row-details" slot-scope="data">
        <b-card>
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Nonce:</b></b-col>
            <b-col>{{ data.item.nonce }}</b-col>
          </b-row>

          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Public Key:</b></b-col>
            <b-col>{{ data.item.publicKey }}</b-col>
          </b-row>
        </b-card>
      </template>


    </b-table>
  </div>
</template>

<script>
  export default {
    name: 'account-table',
    props: {
      fields: Array,
      accounts: Array,
    },
  }
</script>
