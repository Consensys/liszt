<template>
  <div id="app" class="small-container">
    <h3>Transfer</h3>
    <transfer-form @add:transfer="addTransfer" v-bind:transfer="transfer" v-bind:rIdName="rIdName"/>

    <h3>Accounts</h3>
    <account-table @load:data="loadData" v-bind:accounts="accounts" v-bind:fields="fields"/>
  </div>
</template>

<script>
import TransferForm from '@/components/TransferForm.vue'
import AccountTable from '@/components/AccountTable.vue'

const PORT = process.env.VUE_APP_API_PORT
const ROLLUP_ID = process.env.VUE_APP_ROLLUP_ID

function rollupName(id) {
   if (id ==0){
        return 'Rollup A';
   }else{
        return 'Rollup B'
   }
}

export default {
  name: 'app',
  components: {
    TransferForm,
    AccountTable
  },

  data() {
    return {
      fields: ['index','owner','isTmp' ,'balance', 'show_details'],
      rIdName: rollupName(ROLLUP_ID),
      accounts: [],
      transfer:  {
        from: '',
        rIdFrom: ROLLUP_ID,
        to: '',
        rIdTo:'',
        amount:'',
        nonce:'',
        hashOfThePendingTransfer:'',
      },
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

    async addTransfer(transfer) {
            console.error(transfer)

      try {
        const response = await fetch('http://localhost:'+PORT+'/transfers', {
          method: 'POST',
          body: JSON.stringify(transfer),
          headers: { 'Content-type': 'application/json; charset=UTF-8' },
        })
        const data = await response.json()
        console.error(data)
      } catch (error) {
        console.error(error)
      }
    },

    loadData(index){
        this.transfer.from = ''
        this.transfer.to= ''
        this.transfer.rIdTo=''
        this.transfer.amount=''
        this.transfer.hashOfThePendingTransfer=''

        this.transfer.from = this.accounts[index].publicKey
        this.transfer.nonce = this.accounts[index].nonce+1
    },
  },
}
</script>

<style>
  .small-container {
    max-width: 1080px;
    max-height: 100=px;
  }
</style>
