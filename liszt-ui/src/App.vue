<template>
  <div id="app" class="small-container">
    <h1>Accounts</h1>
    <account-table v-bind:accounts="accounts" v-bind:fields="fields"/>
  </div>
</template>

<script>
import AccountTable from '@/components/AccountTable.vue'

const PORT = process.env.VUE_APP_API_PORT

export default {
  name: 'app',
  components: {
    AccountTable
  },

  data() {
    return {
      fields: ['index','owner','isTmp' ,'balance', 'show_details'],
      accounts: []
    }
  },

  mounted() {
    this.getAccounts()
  },

  methods: {
    async getAccounts() {
      try {
        const response = await fetch('http://localhost:'+PORT+'/accounts')
        const data = await response.json()
        this.accounts = data
      } catch (error) {
        console.error(error)
      }
    },
  },
}
</script>

<style>
  .small-container {
    max-width: 1080px;
    max-height: 1050px;
  }
</style>
