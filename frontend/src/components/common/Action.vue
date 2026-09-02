<script setup>
const props = defineProps({
    message: String,
    resultTrue: String,
    resultFalse: String
})

const emit = defineEmits([
    "result"
])

const reuslt = (value) =>{
    emit("result", value)
}

</script>

<template>
    <div>
        {{ message }}
    </div>
    <button @click="reuslt(true)">
        {{ props.resultTrue }}
    </button>
    <button @click="reuslt(false)">
        {{ props.resultFalse }}
    </button>
</template>