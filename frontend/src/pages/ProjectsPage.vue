<template>
  <div class="wrapper">

    <!--Management Block-->
    <div class="management-block bg-secondary">

      <b-button @click="resetFilters" v-if="filters.visible" class="text-warning">
        <b-icon icon="x-circle" aria-hidden="true"></b-icon>
      </b-button>

      <b-button @click="reloadItems" v-if="filters.visible" class="text-success">
        <b-icon icon="search" aria-hidden="true"></b-icon>
      </b-button>

      <b-button @click="changeFiltersVisibility">
        <b-icon icon="filter" aria-hidden="true"></b-icon>
      </b-button>

      <b-button>
        <b-icon icon="plus" aria-hidden="true"></b-icon>
      </b-button>
    </div>

    <!--Filters Block-->
    <b-collapse class="filters-block text-secondary" v-model="filters.visible">
      <b-container fluid>
        <b-row>

          <b-col>
            <b-form-group label="Name:" size="sm">
              <b-form-input v-model="filters.name" trim placeholder="Enter name" size="sm"
                @keyup.enter="reloadItems"></b-form-input>
            </b-form-group>

            <b-form-group label="Code:" size="sm">
              <b-form-input v-model="filters.code" trim placeholder="Enter code" size="sm"
                @keyup.enter="reloadItems"></b-form-input>
            </b-form-group>
          </b-col>

          <b-col>
            <b-form-group label="Created From:" size="sm">
              <DataTimePicker v-model:datetime="filters.dateCreatedFrom" ref="createdFrom" />
            </b-form-group>


            <b-form-group label="Created To:" size="sm">
              <DataTimePicker v-model:datetime="filters.dateCreatedTo" ref="createdTo" />
            </b-form-group>
          </b-col>

          <b-col>
            <b-form-group label="Updated From:" size="sm">
              <DataTimePicker v-model:datetime="filters.dateUpdatedFrom" ref="updatedFrom" />
            </b-form-group>

            <b-form-group label="Updated To:" size="sm">
              <DataTimePicker v-model:datetime="filters.dateUpdatedTo" ref="updatedTo" />
            </b-form-group>
          </b-col>

        </b-row>
      </b-container>
    </b-collapse>

    <!--Table Block-->
    <b-table class="table-block" striped hover no-local-sorting show-empty :busy="table.busy" :items="table.items"
      :fields="table.fields" v-model:sort-by="table.sortBy" v-model:sort-desc="table.sortDesc">

      <template #empty>
        Projects not founded
      </template>

      <template #cell(dateCreated)="data">
        {{ dateFormate(data.value) }}
      </template>

      <template #cell(dateUpdated)="data">
        {{ dateFormate(data.value) }}
      </template>

      <template #cell(color)="data">
        <div class="color-circl" v-bind:style="{ backgroundColor: data.value }">
        </div>
      </template>

    </b-table>
    
    <!--Pagination Block-->
    <b-pagination
      v-model="table.pageNumber"

    ></b-pagination>
  </div>

</template>

<script>
import projectService from '@/services/projectService';
import DataTimePicker from '@/components/DateTimePicker.vue';

export default {
  name: 'ProjectsPage',
  data() {
    return {
      filters: {
        visible: true,
        code: null,
        name: null,
        dateCreatedFrom: null,
        dateCreatedTo: null,
        dateUpdatedFrom: null,
        dateUpdatedTo: null
      },
      table: {
        busy: true,
        pageNumber: 0,
        pageSize: 50,
        sortBy: 'dateCreated',
        sortDesc: false,
        fields: [
          {
            key: 'id',
            label: 'Id',
            sortable: true
          },
          {
            key: 'dateCreated',
            label: 'Created',
            sortable: true,
          },
          {
            key: 'dateUpdated',
            label: 'Updated',
            sortable: true,
          },
          {
            key: 'color',
            label: 'Color',
            sortable: true,
          },
          {
            key: 'code',
            label: 'Code',
            sortable: true,
          },
          {
            key: 'name',
            label: 'Name',
            sortable: true
          },
          'description'
        ],
        items: []
      }

    }
  },
  mounted() {
    this.reloadItems()
  },
  methods: {

    changeFiltersVisibility() {
      this.filters.visible = !this.filters.visible
    },

    resetFilters() {
      this.filters = {
        visible: this.filters.visible,
        code: null,
        name: null,
        dateCreatedFrom: null,
        dateCreatedTo: null,
        dateUpdatedFrom: null,
        dateUpdatedTo: null
      }
      this.$refs.createdFrom.reset()
      this.$refs.createdTo.reset()
      this.$refs.updatedFrom.reset()
      this.$refs.updatedTo.reset()
    },

    dateFormate(date) {
      const options = { year: 'numeric', month: 'numeric', day: 'numeric', hour: "numeric", minute: "numeric", second: "numeric" }
      return new Date(date).toLocaleDateString("ru", options)
    },

    reloadItems() {
      this.table.busy = true
      projectService.getProjects({
        pageSize: this.table.pageSize,
        pageNumber: this.table.pageNumber,
        sortBy: this.table.sortBy,
        sortOrder: this.table.sortDesc ? 'DESC' : 'ASC',
        code: this.filters.code,
        name: this.filters.name,
        dateCreatedFrom: this.filters.dateCreatedFrom,
        dateCreatedTo: this.filters.dateCreatedTo,
        dateUpdatedFrom: this.filters.dateUpdatedFrom,
        dateUpdatedTo: this.filters.dateUpdatedTo
      }).then((data) => {
        this.table.items = data
        this.table.busy = false
      })
    }
  },

  watch: {
    'table.sortDesc'() {
      this.reloadItems()
    }
  },
  components: {
    DataTimePicker
  }
}
</script>

<style scoped>
.wrapper {
  font-size: 14px;
}

.filters-block {
  color: #fff;
  padding: 15px;
}

.management-block {
  display: flex;
  justify-content: right;
  padding: 2px 0;
}

.table-block {
  text-align: center;
}

.color-circl {
  display: block;
  height: 30px;
  width: 30px;
  border-radius: 50%;
  margin: 0 auto;
}

.header {
  background-color: #000;
  width: 100%;
  height: 50px;
}
</style>