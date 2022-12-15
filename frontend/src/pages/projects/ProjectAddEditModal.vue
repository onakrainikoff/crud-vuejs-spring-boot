<template>
    <b-modal id="modal-add-edit" :title="title" centered>
        <b-form-group label="Code:" :invalid-feedback="field.code.message">
            <b-form-input v-model="field.code.value" placeholder="Enter code" :state="field.code.state"
                @change="validateCode"></b-form-input>
        </b-form-group>

        <b-form-group label="Name:" :invalid-feedback="field.name.message">
            <b-form-input v-model="field.name.value" placeholder="Enter name" :state="field.name.state"
                @change="validateName"></b-form-input>
        </b-form-group>

        <b-form-group label="Description:" :invalid-feedback="field.description.message">
            <b-form-input v-model="field.description.value" placeholder="Enter description"
                :state="field.description.state" @change="validateDescription"></b-form-input>
        </b-form-group>

        <b-alert :show="error !== null" variant="danger">{{ error }}</b-alert>

        <template #modal-footer>
            <b-button class="float-right" @click="hide">
                Cancel
            </b-button>

            <b-button variant="primary" class="float-right" @click="save">
                {{ okButton }}
            </b-button>
        </template>

    </b-modal>
</template>

<script>
import projectService from '@/services/ProjectService';

export default {
    name: 'ProjectAddEditModal',
    data() {
        return {
            id: null,
            title: null,
            okButton: null,
            error: null,
            field: {
                code: {
                    value: null,
                    state: null,
                    message: null,
                },
                name: {
                    value: null,
                    state: null,
                    message: null,
                },
                description: {
                    value: null,
                    state: null,
                    message: null,
                }
            }
        }
    },
    mounted() {
        this.resetData()
    },
    emits: ['success'],
    methods: {
        resetData() {
            this.id = null
            this.title = null
            this.okButton = null
            this.error = null
            this.field = {
                code: {
                    value: null,
                    state: null,
                    message: null,
                },
                name: {
                    value: null,
                    state: null,
                    message: null,
                },

                description: {
                    value: null,
                    state: null,
                    message: null,
                }
            }
        },

        showAddModal() {
            this.resetData()
            this.id = null
            this.okButton = 'Add'
            this.title = 'Add new Project'
            this.$bvModal.show('modal-add-edit')
        },

        showEditModal(item) {
            this.resetData()
            this.okButton = 'Edit'
            this.id = item.id
            this.title = `Edit Project #${this.id}`
            this.field.code.value = item.code
            this.field.name.value = item.name
            this.field.description.value = item.description
            this.$bvModal.show('modal-add-edit')
        },

        hide() {
            this.$bvModal.hide('modal-add-edit')
        },

        save() {
            const validation = [
                this.validateCode(this.field.code.value),
                this.validateName(this.field.name.value),
                this.validateDescription(this.field.description.value)
            ]
            if (validation.some(valid => valid === false)) {
                return
            }
            let promise;
            if (this.id !== null) {
                promise = projectService.editProject({
                    id: this.id,
                    name: this.field.name.value,
                    code: this.field.code.value,
                    description: this.field.description.value
                })
            } else {
                promise = projectService.addProject({
                    name: this.field.name.value,
                    code: this.field.code.value,
                    description: this.field.description.value
                })
            }
            promise.then(() => {
                this.$emit('success')
                this.hide()
            }).catch(error => {
                console.log(JSON.stringify(error));
                this.error = "Error: " + error.message ?? error
            })
        },

        validateCode(value) {
            const field = this.field.code
            value = value ?? ''
            if (value.length < 3) {
                field.state = false
                field.message = "code must be more than 3 characters "
            } else if (value.length > 50) {
                field.state = false
                field.message = "code must be less than 50 characters "
            } else {
                field.state = true
            }
            return field.state
        },

        validateName(value) {
            const field = this.field.name
            value = value ?? ''
            if (value.length < 3) {
                field.state = false
                field.message = "name must be more than 3 characters "
            } else if (value.length > 100) {
                field.state = false
                field.message = "name must be less than 100 characters "
            } else {
                field.state = true
            }
            return field.state
        },

        validateDescription(value) {
            const field = this.field.description
            value = value ?? ''
            if (value.length < 3) {
                field.state = false
                field.message = "description must be more than 3 characters "
            } else if (value.length > 250) {
                field.state = false
                field.message = "description must be less than 250 characters "
            } else {
                field.state = true
            }
            return field.state
        },
    }
}


</script>
