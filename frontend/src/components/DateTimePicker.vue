<template>
    <div class="wrapper" style="display: flex;">

        <b-form-datepicker size="sm" class="date" :style="{ width: widthDate }" today-button reset-button
            :locale="locale" :date-format-options="dateFormat" :placeholder="datePlaceholder" v-model="dateValue"
            @context="onContextDate" />
        <b-form-timepicker size="sm" class="time" :style="{ width: widthTime }" show-seconds :locale="locale"
            :placeholder="timePlaceholder" v-model="timeValue" @context="onContextTime" :disabled="(dateValue === null)"
            :state="state" no-close-button reset-button now-button />
    </div>
</template>

<script>
export default {
    name: 'DateTimePicker',
    props: {
        datetime: {
            type: Date
        },
        widthDate: {
            type: String,
            default: '60%'
        },
        widthTime: {
            type: String,
            default: '40%'
        },

        datePlaceholder: {
            type: String,
            default: '-'
        },
        timePlaceholder: {
            type: String,
            default: '-'
        },
        locate: {
            type: String,
            default: 'ru'
        },
        dateFormat: {
            type: Object,
            default: function () {
                return { year: 'numeric', month: 'numeric', day: 'numeric' }
            }
        }
    },
    emits: ['update:datetime'],
    mounted() {
        this.onSetDateTime()
    },
    data() {
        return {
            dateValue: null,
            timeValue: null,
            selectedDate: null,
            selectedTime: null,
            state: null
        }
    },
    watch: {
        datetime() {
            this.onSetDateTime()
        }
    },
    methods: {
        onSetDateTime() {
            if (this.datetime) {
                this.dateValue = this.datetime
                this.timeValue = `${this.datetime.getHours()}:${this.datetime.getMinutes()}:${this.datetime.getSeconds()}`
            } else {
                this.reset()
            }
        },
        reset() {
            this.dateValue = null
            this.timeValue = null
            this.selectedDate = null
            this.selectedTime = null
            this.state = null
        },
        onContextDate({ selectedDate }) {
            this.selectedDate = selectedDate
            if (this.selectedDate === null) {
                this.selectedTime = null
                this.timeValue = null
            }
            this.updateModelValue()

        },

        onContextTime({ hours, minutes, seconds }) {
            this.selectedHours = hours
            this.selectedMinutes = minutes
            this.selectedSeconds = seconds
            if (hours === null || minutes === null || seconds === null) {
                this.selectedTime = null
            } else {
                this.selectedTime = { hours, minutes, seconds }
            }
            this.updateModelValue()
        },

        updateModelValue() {
            if (this.selectedDate && this.selectedTime === null) {
                this.state = false
            } else {
                this.state = null
            }
            if (this.selectedDate && this.selectedTime) {
                const date = new Date(this.selectedDate.getTime())
                date.setHours(this.selectedTime.hours)
                date.setMinutes(this.selectedTime.minutes)
                date.setSeconds(this.selectedTime.seconds)
                this.$emit('update:datetime', date)
            } else {
                this.$emit('update:datetime', null)
            }
        }
    }
}
</script>
