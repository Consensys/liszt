<template id="account-table">
  <div>
    <b-table :items="accounts" :fields="fields" striped sticky-header ="400px" bordered borderless head-variant	="dark" responsive="sm">
      <template slot="[show_details]" slot-scope="row">
        <b-button size="sm" @click="row.toggleDetails" class="mr-2">
          {{ row.detailsShowing ? 'Hide' : 'Show'}} Details
        </b-button>
      </template>

      <template slot="row-details" slot-scope="row">
        <b-card>
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Nonce:</b></b-col>
            <b-col>{{ row.item.nonce }}</b-col>
          </b-row>

          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Is Active:</b></b-col>
            <b-col>{{ row.item.isActive }}</b-col>
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
