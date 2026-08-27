<script setup>
defineProps({
    message: String
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
        확인
    </button>
    <button @click="reuslt(false)">
        취소
    </button>
</template>