<template id="operations-form">
  <div>
      <b-form>
      <b-form-row>
        <b-col cols>
            <b-form-group label="Block height">
            <b-form-input :disabled="true" v-model="nodeInfo.blockHeight"  placeholder="0"/>
            </b-form-group>
        </b-col cols>

        <b-col cols>
            <b-form-group label="Last root hash">
            <b-form-input v-model="nodeInfo.lastRootHash" :disabled="true"  placeholder="0"/>
            </b-form-group>
            </b-col cols>
      </b-form-row>
           <h3>Transfers</h3>
      <b-form-row>
      </b-form-row>

       </b-form>
  </div>
</template>


<script>
  export default {
    name: 'operations-form',
    props: {
      nodeInfo: Object,
    },
  }
</script>

