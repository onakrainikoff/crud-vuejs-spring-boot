<template>
  <div class="wrapper">
    <!--Management Block-->
    <div class="management-block bg-info">
      <div class="label h5 text-light">Tasks:</div>
      <b-button @click="changeFiltersVisibility" variant="dark" class="action-btn">
        <b-icon icon="filter" aria-hidden="true"></b-icon>
      </b-button>

      <b-button @click="addItem" variant="dark" class="action-btn">
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
        <b-row>
          <b-col>
            <b-button class="float-right action-btn" @click="reloadItems" v-if="filters.visible" size="sm"
              variant="primary">
              <b-icon icon="search" aria-hidden="true"></b-icon>
            </b-button>

            <b-button class="float-right action-btn" @click="resetFilters" v-if="filters.visible" size="sm"
              variant="danger">
              <b-icon icon="x-circle" aria-hidden="true"></b-icon>
            </b-button>
          </b-col>
        </b-row>

      </b-container>
    </b-collapse>

    <!--Table Block-->
    <!-- <b-table class="table-block" striped hover no-local-sorting show-empty :busy="table.busy" :items="table.items"
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

      <template #cell(action)="data">
        <b-button class="action-btn" @click="() => editItem(data.item)" size="sm" variant="outline-secondary">
          <b-icon icon="pencil" aria-hidden="true"></b-icon>
        </b-button>
        <b-button class="action-btn" @click="() => deleteItem(data.item)" size="sm" variant="outline-secondary">
          <b-icon icon="trash" aria-hidden="true"></b-icon>
        </b-button>
      </template>

    </b-table> -->

    <!--Pagination Block-->
    <b-pagination align="center" v-model="table.pageNumber" :per-page="table.pageSize"
      :total-rows="table.totalElements"></b-pagination>

    <!--Modals Block-->
    <ProjectAddEditModal ref="addEditModal" @success="reloadItems" />
  </div>

</template>

<script>
import projectService from '@/services/ProjectService';
import DataTimePicker from '@/components/DateTimePicker.vue';
import ProjectAddEditModal from '@/views/projects/ProjectAddEditModal.vue';

export default {
  name: 'ProjectsPage',

  data() {
    return {
      filters: {
        visible: false,
        code: null,
        name: null,
        dateCreatedFrom: null,
        dateCreatedTo: null,
        dateUpdatedFrom: null,
        dateUpdatedTo: null
      },
      table: {
        busy: true,
        pageNumber: 1,
        pageSize: 10,
        totalElements: 0,
        sortBy: 'dateCreated',
        sortDesc: true,
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
            key: 'code',
            label: 'Code',
            sortable: true,
          },
          {
            key: 'name',
            label: 'Name',
            sortable: true
          },
          'description',
          {
            key: 'action',
            label: '#',
          },
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
        pageNumber: this.table.pageNumber - 1,
        sortBy: this.table.sortBy,
        sortOrderDesc: this.table.sortDesc,
        code: this.filters.code,
        name: this.filters.name,
        dateCreatedFrom: this.filters.dateCreatedFrom,
        dateCreatedTo: this.filters.dateCreatedTo,
        dateUpdatedFrom: this.filters.dateUpdatedFrom,
        dateUpdatedTo: this.filters.dateUpdatedTo
      }).then((data) => {
        this.table.items = data.content
        this.table.totalElements = data.totalElements
        this.table.busy = false
      })
    },

    addItem() {
      this.$refs.addEditModal.showAddModal()
    },

    editItem(item) {
      this.$refs.addEditModal.showEditModal(item)
    },

    deleteItem(item) {
      this.$bvModal.msgBoxConfirm(`Are you sure you want to delete project #${item.id}?`, {
        title: `Delete project #${item.id}`,
        okVariant: 'danger',
        okTitle: 'Delete',
        cancelTitle: 'Cancel',
        centered: true
      }).then(value => {
        if(value) {
          projectService.deleteProject({id: item.id})
            .then(()=>{
              this.reloadItems()
            })
            .catch(error => {
              console.log(JSON.stringify(error));
            })
        }
      })
    }

  },

  watch: {
    'table.sortDesc'() {
      this.reloadItems()
    },

    'table.pageNumber'() {
      this.reloadItems()
    }
  },
  components: {
    DataTimePicker,
    ProjectAddEditModal
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
  align-items: center;
  padding: 2px 0;
}
.label{
  width: 100%;
  padding: 0 15px;
}

.table-block {
  text-align: center;
}

.action-btn {
  margin: 0 2px;
}
</style>