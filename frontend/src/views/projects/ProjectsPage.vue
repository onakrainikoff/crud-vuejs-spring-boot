<template>
  <div class="wrapper">
    <!--Header block-->
    <div class="header-block">
      <b-breadcrumb class="header">
        <b-breadcrumb-item href="/">
          <b-icon icon="house-fill" scale="1.25" shift-v="1.25" aria-hidden="true"></b-icon>
          Main
        </b-breadcrumb-item>
        <b-breadcrumb-item active>Projects</b-breadcrumb-item>
      </b-breadcrumb>

      <b-button @click="togleFilters" variant="dark" class="action-btn">
        <b-icon icon="filter" aria-hidden="true"></b-icon>
      </b-button>

      <b-button @click="addItem" variant="dark" class="action-btn">
        <b-icon icon="plus" aria-hidden="true"></b-icon>
      </b-button>
    </div>

    <!--Filters Block-->
    <b-collapse class="filters-block text-secondary" v-model="filtersVisible">
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
            <b-button class="float-right action-btn" @click="reloadItems" v-if="filtersVisible" size="sm"
              variant="primary">
              <b-icon icon="search" aria-hidden="true"></b-icon>
            </b-button>

            <b-button class="float-right action-btn" @click="resetFilters" v-if="filtersVisible" size="sm"
              variant="danger">
              <b-icon icon="x-circle" aria-hidden="true"></b-icon>
            </b-button>
          </b-col>
        </b-row>

      </b-container>
    </b-collapse>

    <!--Table Block-->
    <b-table class="table-block" striped hover no-local-sorting show-empty :busy="table.busy" :items="table.items"
      :fields="table.fields" v-model:sort-by="table.sorting.sortBy" v-model:sort-desc="table.sorting.sortDesc">

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

    </b-table>

    <!--Pagination Block-->
    <b-pagination align="center" v-model="table.pagination.pageNumber" :per-page="table.pagination.pageSize"
      :total-rows="table.pagination.totalElements"></b-pagination>

    <!--Modals Block-->
    <ProjectModal ref="addEditModal" @success="reloadItems" />
  </div>

</template>

<script>
import projectService from '@/services/ProjectService';
import DataTimePicker from '@/components/DateTimePicker.vue';
import ProjectModal from '@/views/projects/ProjectModal.vue';

export default {
  name: 'ProjectsPage',

  data() {
    return {
      filtersVisible: false,
      filters: {
        code: null,
        name: null,
        dateCreatedFrom: null,
        dateCreatedTo: null,
        dateUpdatedFrom: null,
        dateUpdatedTo: null
      },
      table: {
        busy: true,

        sorting: {
          sortBy: 'dateCreated',
          sortDesc: true
        },

        pagination: {
          pageNumber: 1,
          pageSize: 10,
          totalElements: 0
        },

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
    this.setUpFilters()
    Object.keys(this.filters).forEach(key => {
      this.$watch(`filters.${key}`, () => this.saveFilters())
    })
    this.reloadItems()
  },

  methods: {
    setUpFilters() {
      let visible = false
      Object.keys(this.filters).forEach(key => {
        if (this.$route.query[key]) {
          if (key.startsWith('date')) {
            this.filters[key] = new Date(Number(this.$route.query[key]))
          } else {
            this.filters[key] = this.$route.query[key]
          }
          visible = true
        }
      })
      this.filtersVisible = visible
    },

    saveFilters() {
      let patch = null
      Object.keys(this.filters).forEach(key => {
        const filter = this.filters[key]
        const query = this.$route.query[key]
        if (filter !== query) {
          patch = patch ?? {}
          if (filter !== null) {
            if (key.startsWith('date')) {
              patch[key] = filter.getTime()
            } else {
              patch[key] = filter
            }
          }
        }
      })
      if(patch !== null) {
        this.$router.push({ query: patch })
      }
    },

    togleFilters() {
      this.filtersVisible = !this.filtersVisible
    },

    resetFilters() {
      this.filters = {
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

    reloadItems() {
      this.table.busy = true
      projectService.getProjects({
        pageSize: this.table.pagination.pageSize,
        pageNumber: this.table.pagination.pageNumber - 1,
        sortBy: this.table.sorting.sortBy,
        sortOrderDesc: this.table.sorting.sortDesc,
        code: this.filters.code,
        name: this.filters.name,
        dateCreatedFrom: this.filters.dateCreatedFrom,
        dateCreatedTo: this.filters.dateCreatedTo,
        dateUpdatedFrom: this.filters.dateUpdatedFrom,
        dateUpdatedTo: this.filters.dateUpdatedTo
      }).then((data) => {
        this.table.items = data.content
        this.table.pagination.totalElements = data.totalElements
        this.table.busy = false
      }).catch(error => {
        console.log(JSON.stringify(error));
        this.$store.dispatch('addErrorAlert', { message: "Error: " + error.message ?? error })
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
        if (value) {
          projectService.deleteProject({ id: item.id })
            .then(() => {
              this.reloadItems()
              this.$store.dispatch('addSuccessAlert', { message: `Project #${item.id} deleted` })
            })
            .catch(error => {
              console.log(JSON.stringify(error));
              this.$store.dispatch('addErrorAlert', { message: "Error: " + error.message ?? error })
            })
        }
      })
    },

    dateFormate(date) {
      const options = { year: 'numeric', month: 'numeric', day: 'numeric', hour: "numeric", minute: "numeric", second: "numeric" }
      return new Date(date).toLocaleDateString("ru", options)
    }
  },


  watch: {
    'table.sorting.sortDesc'() {
      this.reloadItems()
    },

    'table.pagination.pageNumber'() {
      this.reloadItems()
    },
  },

  components: {
    DataTimePicker,
    ProjectModal
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

.header-block {
  display: flex;
  justify-content: right;
  align-items: center;
  padding: 2px 0;
  background-color: #E9ECEF;
}

.header-block .header {
  width: 100%;
  margin: 0;
}

.table-block {
  text-align: center;
}

.action-btn {
  margin: 0 2px;
}
</style>